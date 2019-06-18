import React from 'react';
import {createStackNavigator, createAppContainer} from 'react-navigation';
import HomeScreen from './screens/HomeScreen'
import UpdateTask from './screens/UpdateTask'
import AddTask from './screens/AddTask'

const App = createStackNavigator({
  HomeScreen: {
    screen: HomeScreen,
    navigationOptions: {
      title: 'Todo ✔️',
      headerStyle: { backgroundColor: "#efb13e"},
      headerTintColor: 'white',
    },
  },
  AddTask: {
    screen: AddTask,
    navigationOptions: {
      title: 'Add Task',
      headerStyle: { backgroundColor: "#efb13e"},
      headerTintColor: 'white',
    },
  },
  UpdateTask: {
    screen: UpdateTask,
    navigationOptions: {
      title: 'Update Task',
      headerStyle: { backgroundColor: "#efb13e"},
      headerTintColor: 'white',
    },
  },
});

export default createAppContainer(App);