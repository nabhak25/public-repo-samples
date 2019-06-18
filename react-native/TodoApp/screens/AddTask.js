import React from "react";
import {
  View,
  ScrollView,
  KeyboardAvoidingView,
  Alert,
  CheckBox,
  Text,
  Switch,
  Platform
} from "react-native";
import Mytextinput from "./components/Mytextinput";
import Mybutton from "./components/Mybutton";
import { openDatabase } from "react-native-sqlite-storage";
var db = openDatabase({ name: "TodoTasks.db" });
class AddTask extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      task: "",
      description: "",
      finish_by: "",
      finished: false
    };
  }

  add_task = () => {
    var that = this;
    const { task } = this.state;
    const { description } = this.state;
    const { finish_by } = this.state;
    const { finished } = this.state;
    if (task) {
      if (description) {
        if (finish_by) {
          db.transaction(function(tx) {
            tx.executeSql(
              "INSERT INTO table_task (task, description, finish_by, finished) VALUES (?,?,?,?)",
              [task, description, finish_by, finished],
              (tx, results) => {
                console.log("Results", results.rowsAffected);
                if (results.rowsAffected > 0) {
                  Alert.alert(
                    "Success",
                    "Task Added Successfully",
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
                  alert("Could not add task. Try again.");
                }
              }
            );
          });
        } else {
          alert("Finish by required");
        }
      } else {
        alert("Description required");
      }
    } else {
      alert("Task required");
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
            <Mytextinput
              placeholder="Enter task"
              onChangeText={task => this.setState({ task })}
              style={{ padding: 10 }}
            />
            <Mytextinput
              placeholder="Enter Description"
              onChangeText={description => this.setState({ description })}
              style={{ padding: 10, textAlignVertical: "top" }}
              maxLength={225}
              numberOfLines={5}
              multiline={true}
            />
            <Mytextinput
              placeholder="Enter Finish By"
              onChangeText={finish_by => this.setState({ finish_by })}
              style={{ padding: 10 }}
            />
            {Platform.OS === "android" ? (
              <View style={{ flexDirection: "row", padding: 10 }}>
                <CheckBox
                  onValueChange={finished => this.setState({ finished })}
                  value={this.state.finished}
                />
                <Text style={{ marginTop: 5 }}> Mark as finished </Text>
              </View>
            ) : (
              <View style={{ flexDirection: "row", padding: 10 }}>
                <Switch
                  onValueChange={finished => this.setState({ finished })}
                  value={this.state.finished}
                />
                <Text style={{ marginTop: 5 }}> Mark as finished </Text>
              </View>
            )}

            <Mybutton title="Add" customClick={this.add_task.bind(this)} />
          </KeyboardAvoidingView>
        </ScrollView>
      </View>
    );
  }
}

export default AddTask;
