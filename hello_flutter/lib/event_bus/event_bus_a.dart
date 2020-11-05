import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_flutter/event_bus/event_bus.dart';
import 'package:hello_flutter/event_bus/event_bus_b.dart';

class EventBusA extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _EventBusAState();
}

class _EventBusAState extends State<EventBusA> {
  String _text = '';

  @override
  void initState() {
    super.initState();
    bus.on('eventBusA', (args) {
      setState(() {
        _text = args;
      });
    });
  }

  @override
  void dispose() {
    bus.off('eventBusA');
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: SafeArea(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Center(
                child: Text('content: $_text'),
              ),
              RaisedButton(
                child: Text('跳转'),
                onPressed: () {
                  Navigator.push(context, MaterialPageRoute(builder: (context) {
                    return EventBusB();
                  }));
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
