import React from "react";
import {
  View,
  Image,
  Text,
  FlatList,
  StyleSheet,
  ActivityIndicator
} from "react-native";
import { bold } from "ansi-colors";

export default class BitcoinLive extends React.Component {
  constructor(props) {
    super(props);
    this.state = { bitcoin_value: "", isLoading: false, index: "" };
  }

  itemSelected = key => {
    console.log("value: " + key);
    console.log("first three " + key.substring(0, 3));
    this.setState({
      isLoading: true
    });
    fetch(
      "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC" +
        key.substring(0, 3),
      {
        method: "GET"
      }
    )
      .then(response => response.json())
      .then(responseJson => {
        console.log(responseJson);
        this.setState({
          bitcoin_value: responseJson,
          index: key,
          isLoading: false
        });
      })
      .catch(error => {
        Alert.alert(
          "No Internet",
          "Please connect to Wifi or switch on mobile data to see current bitcoin value in " +
            key
        );
        this.setState({
          bitcoin_value: "",
          isLoading: false
        });
      });
  };

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.header}>Bitcoin Live!</Text>
        <Image source={require("./images/bitcoin.png")} style={styles.logo} />
        <Text style={styles.bitcoin_value}>
          {this.state.index.split(" ")[1]}
          {this.state.bitcoin_value.last}
        </Text>
        {this.state.isLoading ? (
          <View>
            <ActivityIndicator color="white" />
          </View>
        ) : null}
        <FlatList
          style={{ marginTop: 12 }}
          data={countries}
          renderItem={({ item }) => (
            <Text
              style={{ fontSize: 24, marginTop: 12 }}
              onPress={() => this.itemSelected(item.key)}
            >
              {item.key}
            </Text>
          )}
        />
      </View>
    );
  }
}

const countries = [
  { key: "AUD $" },
  { key: "BRL R$" },
  { key: "CAD $" },
  { key: "CNY ¥" },
  { key: "EUR €" },
  { key: "GBP £" },
  { key: "HKD $" },
  { key: "IDR Rp" },
  { key: "ILS ₪" },
  { key: "INR ₹" },
  { key: "JPY ¥" },
  { key: "MXN $" },
  { key: "NOK kr" },
  { key: "NZD $" },
  { key: "PLN zł" },
  { key: "RON lei" },
  { key: "RUB ₽" },
  { key: "SEK kr" },
  { key: "SGD R$" },
  { key: "USD R$" },
  { key: "ZAR R" }
];

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "flex-start",
    alignItems: "center",
    backgroundColor: "gray",
    width: "100%"
  },
  header: {
    marginTop: 24,
    fontSize: 30,
    color: "orange",
    fontWeight: "bold"
  },
  logo: {
    width: 100,
    height: 100,
    marginTop: 12
  },
  bitcoin_value: {
    fontSize: 40,
    fontWeight: "bold",
    marginTop: 24,
    color: "white"
  }
});
