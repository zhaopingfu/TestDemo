import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_flutter/event_bus/event_bus.dart';
import 'package:hello_flutter/event_bus/event_bus_b.dart';

class EventBusA extends StatefulWidget {
  const EventBusA({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _EventBusAState();
}

class _EventBusAState extends State<EventBusA> {
  String _text = '';

  @override
  void initState() {
    super.initState();
    bus.on('eventBusA', (dynamic args) {
      setState(() {
        _text = args.toString();
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
      body: SafeArea(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Center(
              child: Text('content: $_text'),
            ),
            ElevatedButton(
              child: const Text('跳转'),
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (BuildContext context) {
                  return const EventBusB();
                }));
              },
            ),
          ],
        ),
      ),
    );
  }
}
