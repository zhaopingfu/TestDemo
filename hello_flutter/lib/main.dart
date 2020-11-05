import 'package:flutter/material.dart';
import 'package:hello_flutter/custom_paint_route.dart';
import 'package:hello_flutter/event_bus/event_bus_a.dart';
import 'package:hello_flutter/gesture/both_direction_test_route.dart';
import 'package:hello_flutter/gesture/gesture_conflict_test.dart';
import 'package:hello_flutter/gesture/study_drag.dart';
import 'package:hello_flutter/gesture/study_gesture_detector.dart';
import 'package:hello_flutter/gesture/study_gesture_recognizer.dart';
import 'package:hello_flutter/gesture/study_listener.dart';
import 'package:hello_flutter/gesture/study_scale.dart';
import 'package:hello_flutter/gradient_circular_progress_indicator.dart';
import 'package:hello_flutter/sample/custom_paint_sample.dart';
import 'package:hello_flutter/sample/gradient_circular_progress_sample.dart';
import 'package:hello_flutter/sample/text_field_sample.dart';
import 'package:hello_flutter/sample/turn_box_sample.dart';
import 'package:hello_flutter/turn_box.dart';
import 'package:hello_flutter/text_field.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return TextFieldDemo();
                }));
              },
              child: Text("跳转文本demo"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return TurnBoxRoute();
                }));
              },
              child: Text("TurnBox"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return CustomPaintRoute();
                }));
              },
              child: Text("CustomPainter"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return GradientCircularProgressRoute();
                }));
              },
              child: Text("GradientCircularProgressIndicator"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return StudyListener();
                }));
              },
              child: Text("study listener"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return StudyGestureDetectorRoute();
                }));
              },
              child: Text("study GestureDetector"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return StudyDrag();
                }));
              },
              child: Text("study Drag"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return StudyScaleRoute();
                }));
              },
              child: Text("study scale"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return StudyGestureRecognizer();
                }));
              },
              child: Text("study gesture recognizer"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return BothDirectionTestRoute();
                }));
              },
              child: Text("Both Direction Test"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return GestureConflictTestRoute();
                }));
              },
              child: Text("Gesture Conflict Test"),
            ),
            RaisedButton(
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return EventBusA();
                }));
              },
              child: Text("Event bus"),
            ),
            Container(
              width: double.infinity,
              height: 50,
              color: Colors.red,
            ),
            RaisedButton(
              child: Text('text field sample'),
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return TextFieldSampleRoute();
                }));
              },
            ),
            RaisedButton(
              child: Text('turn box sample'),
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return TurnBoxDemoRoute();
                }));
              },
            ),
            RaisedButton(
              child: Text('custom paint sample'),
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return CustomPaintSampleRoute();
                }));
              },
            ),
            RaisedButton(
              child: Text('gradient circular progress sample'),
              onPressed: () {
                Navigator.push(context, MaterialPageRoute(builder: (_) {
                  return GradientCircularProgressSampleRoute();
                }));
              },
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
}
