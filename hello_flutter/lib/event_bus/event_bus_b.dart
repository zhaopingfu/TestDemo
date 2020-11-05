import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_flutter/event_bus/event_bus.dart';

class EventBusB extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _EventBusBState();
}

class _EventBusBState extends State<EventBusB> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: RaisedButton(
          child: Text('更改'),
          onPressed: () {
            bus.emit('eventBusA', 'hello world');
          },
        ),
      ),
    );
  }
}
