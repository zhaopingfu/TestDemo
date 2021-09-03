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
import 'package:hello_flutter/sample/wave_loading_widget.dart';
import 'package:hello_flutter/turn_box.dart';
import 'package:hello_flutter/text_field.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

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
            const Text('You have pushed the button this many times:'),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const TextFieldDemo();
                }));
              },
              child: const Text('跳转文本demo'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const TurnBoxRoute();
                }));
              },
              child: const Text('TurnBox'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const CustomPaintRoute();
                }));
              },
              child: const Text('CustomPainter'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const GradientCircularProgressRoute();
                }));
              },
              child: const Text('GradientCircularProgressIndicator'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const StudyListener();
                }));
              },
              child: const Text('study listener'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const StudyGestureDetectorRoute();
                }));
              },
              child: const Text('study GestureDetector'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const StudyDrag();
                }));
              },
              child: const Text('study Drag'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const StudyScaleRoute();
                }));
              },
              child: const Text('study scale'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const StudyGestureRecognizer();
                }));
              },
              child: const Text('study gesture recognizer'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const BothDirectionTestRoute();
                }));
              },
              child: const Text('Both Direction Test'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const GestureConflictTestRoute();
                }));
              },
              child: const Text('Gesture Conflict Test'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const EventBusA();
                }));
              },
              child: const Text('Event bus'),
            ),
            Container(
              width: double.infinity,
              height: 50,
              color: Colors.red,
            ),
            ElevatedButton(
              child: const Text('text field sample'),
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const TextFieldSampleRoute();
                }));
              },
            ),
            ElevatedButton(
              child: const Text('turn box sample'),
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const TurnBoxDemoRoute();
                }));
              },
            ),
            ElevatedButton(
              child: const Text('custom paint sample'),
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const CustomPaintSampleRoute();
                }));
              },
            ),
            ElevatedButton(
              child: const Text('gradient circular progress sample'),
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const GradientCircularProgressSampleRoute();
                }));
              },
            ),
            ElevatedButton(
              child: const Text('gradient circular progress sample'),
              onPressed: () {
                Navigator.push<dynamic>(context, MaterialPageRoute<dynamic>(builder: (_) {
                  return const GradientCircularProgressSampleRoute();
                }));
              },
            ),
            _waveWidgets(),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }

  Widget _waveWidgets() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      mainAxisSize: MainAxisSize.min,
      children: const <Widget>[
        SizedBox(
          width: 300,
          height: 300,
          child: WaveLoadingWidget(
            text: '锲',
            fontSize: 215,
            backgroundColor: Colors.lightBlue,
            foregroundColor: Colors.white,
            waveColor: Colors.lightBlue,
          ),
        ),
        SizedBox(
          width: 250,
          height: 250,
          child: WaveLoadingWidget(
            text: '而',
            fontSize: 175,
            backgroundColor: Colors.indigoAccent,
            foregroundColor: Colors.white,
            waveColor: Colors.indigoAccent,
          ),
        ),
      ],
    );
  }
}
