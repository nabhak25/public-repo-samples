import React from "react";
import { View, Text, FlatList, TouchableWithoutFeedback } from "react-native";
import { openDatabase } from "react-native-sqlite-storage";
var db = openDatabase({ name: "TodoTasks.db" });

class HomeScreen extends React.Component {
  static navigationOptions = ({ navigation }) => {
    const { params = {} } = navigation.state;
    return {
      headerRight: (
        <Text
          style={{
            color: "white",
            fontSize: 14,
            marginRight: 10,
            fontWeight: "bold"
          }}
          onPress={() => params.addTask()}
        >
          Add Task
        </Text>
      )
    };
  };

  constructor(props) {
    super(props);
    this.props.navigation.setParams({
      addTask: this.addTaskInternal
    });
    this.state = {
      FlatListItems: []
    };
    db.transaction(function(txn) {
      txn.executeSql(
        "SELECT name FROM sqlite_master WHERE type='table' AND name='table_task'",
        [],
        function(tx, res) {
          console.log("item:", res.rows.length);
          if (res.rows.length == 0) {
            txn.executeSql("DROP TABLE IF EXISTS table_task", []);
            txn.executeSql(
              "CREATE TABLE IF NOT EXISTS table_task(task_id INTEGER PRIMARY KEY AUTOINCREMENT, task VARCHAR(20), description VARCHAR(255), finish_by VARCHAR(20), finished INT(1))",
              []
            );
          }
        }
      );
    });
  }

  componentWillUpdate() {
    db.transaction(tx => {
      tx.executeSql("SELECT * FROM table_task", [], (tx, results) => {
        var temp = [];
        for (let i = 0; i < results.rows.length; ++i) {
          temp.push(results.rows.item(i));
        }
        this.setState({
          FlatListItems: temp
        });
      });
    });
  }

  ListViewItemSeparator = () => {
    return (
      <View
        style={{ height: 0.5, width: "100%", backgroundColor: "#808080" }}
      />
    );
  };

  addTaskInternal = () => {
    console.log("Add Task clicked");
    this.props.navigation.navigate("AddTask");
  };

  actionOnRow = item => {
    console.log("Item:" + item.task);
    this.props.navigation.navigate("UpdateTask", {
        clicked_item_id: item.task_id,
      });
  };

  render() {
    return (
      <View>
        <FlatList
          data={this.state.FlatListItems}
          ItemSeparatorComponent={this.ListViewItemSeparator}
          keyExtractor={(item, index) => index.toString()}
          renderItem={({ item }) => (
            <TouchableWithoutFeedback onPress={() => this.actionOnRow(item)}>
              <View
                key={item.task_id}
                style={{ backgroundColor: "white", padding: 20 }}>
                <Text>Id: {item.task_id}</Text>
                <Text>Task: {item.task}</Text>
                <Text>Description: {item.description}</Text>
                <Text>Finish By: {item.finish_by}</Text>
                <Text>Completed: {item.finished ? "Yes" : "No"}</Text>
              </View>
            </TouchableWithoutFeedback>
          )}
        />
      </View>
    );
  }
}

export default HomeScreen;
