import React from "react";
import {
  View,
  Text,
  ScrollView,
  KeyboardAvoidingView,
  Alert,
  CheckBox,
  Switch,
  Platform,
} from "react-native";
import Mytextinput from "./components/Mytextinput";
import Mybutton from "./components/Mybutton";
import { openDatabase } from "react-native-sqlite-storage";
var db = openDatabase({ name: "TodoTasks.db" });

class UpdateTask extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      taskId: this.props.navigation.state.params.clicked_item_id,
      task: "",
      description: "",
      finish_by: "",
      finished: 0,
    };
    const {taskId} =this.state;
    console.log(this.state.taskId);
    db.transaction(tx => {
      tx.executeSql(
        "SELECT * FROM table_task where task_id = ?",
        [taskId],
        (tx, results) => {
          var len = results.rows.length;
          console.log("len", len);
          if (len > 0) {
            console.log(results.rows.item(0).task);
            this.setState({
                task: results.rows.item(0).task,
                description: results.rows.item(0).description,
                finish_by: results.rows.item(0).finish_by,
                finished: results.rows.item(0).finished,
            });
          } else {
            this.setState({
                task:'',
                description:'',
                finish_by:'',
              });
          }
        }
      );
    });
  }

  updateTask = () => {
    var that = this;
    const { taskId } = this.state;
    const { task } = this.state;
    const { description } = this.state;
    const { finish_by } = this.state;
    const { finished } = this.state;
    if (task) {
      if (description) {
        if (finish_by) {
          db.transaction(tx => {
            tx.executeSql(
              "UPDATE table_task set task=?, description=?, finish_by=?, finished=? where task_id=?",
              [task, description, finish_by, finished, taskId],
              (tx, results) => {
                console.log("Results", results.rowsAffected);
                if (results.rowsAffected > 0) {
                  Alert.alert(
                    "Success",
                    "Task updated successfully",
                    [
                      {
                        text: "Ok",
                        onPress: () =>
                          that.props.navigation.navigate("HomeScreen")
                      }
                    ],
                    { cancelable: false }
                  );
                } else {
                    alert('Updation Failed');
                }
              }
            );
          });
        } else {
            alert('Please fill finish by');
        }
      } else {
        alert('Please fill description');
      }
    } else {
        alert('Please fill task');
    }
  };

  render() {
    return (
      <View style={{ backgroundColor: "white", flex: 1 }}>
        <ScrollView keyboardShouldPersistTaps="handled">
          <KeyboardAvoidingView
            behavior="padding"
            style={{ flex: 1, justifyContent: "space-between" }}
          >
            {/* <Mytextinput
              placeholder="Enter task"
              style={{ padding:10 }}
              onChangeText={input_user_id => this.setState({ input_user_id })}
            /> */}
            {/* <Mybutton
              title="Search User"
              customClick={this.searchUser.bind(this)}
            /> */}
            <Mytextinput
              placeholder="Enter Task"
              value={this.state.task}
              style={{ padding: 10 }}
              onChangeText={task => this.setState({ task })}
            />
            <Mytextinput
              placeholder="Enter Description"
              value={"" + this.state.description}
              onChangeText={description => this.setState({ description })}
              maxLength={225}
              numberOfLines={5}
              multiline={true}
              style={{ padding: 10, textAlignVertical: "top", }}
            />
            <Mytextinput
              value={this.state.finish_by}
              placeholder="Enter Finish By"
              onChangeText={finish_by => this.setState({ finish_by })}
              style={{ padding: 10 }}
            />
            {Platform.OS === 'android' ? (
                <View style={{ flexDirection: "row", padding: 10, }}>
              <CheckBox
                onValueChange={finished => this.setState({ finished })}
                value={this.state.finished == 0 ? false : true}
              />
              <Text style={{ marginTop: 5 }}> Mark as finished </Text>
            </View>
            ) : (<View style={{ flexDirection: "row", padding: 10, }}>
              <Switch
                onValueChange={finished => this.setState({ finished })}
                value={this.state.finished == 0 ? false : true}
              />
              <Text style={{ marginTop: 5 }}> Mark as finished </Text>
            </View>)
            }
            <Mybutton
              title="Update task"
              customClick={this.updateTask.bind(this)}
            />
          </KeyboardAvoidingView>
        </ScrollView>
      </View>
    );
  }
}

export default UpdateTask;
