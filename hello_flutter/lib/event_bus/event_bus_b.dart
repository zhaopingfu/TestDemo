import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_flutter/event_bus/event_bus.dart';

class EventBusB extends StatefulWidget {
  const EventBusB({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _EventBusBState();
}

class _EventBusBState extends State<EventBusB> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: ElevatedButton(
          child: const Text('更改'),
          onPressed: () {
            bus.emit('eventBusA', 'hello world');
          },
        ),
      ),
    );
  }
}
