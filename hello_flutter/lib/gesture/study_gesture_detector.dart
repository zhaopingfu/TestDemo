import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class StudyGestureDetectorRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _StudyGestureDetectorRouteState();
}

class _StudyGestureDetectorRouteState extends State<StudyGestureDetectorRoute> {
  String _operation = '';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          width: double.infinity,
          height: double.infinity,
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Center(
                  child: GestureDetector(
                    child: Container(
                      width: 200,
                      height: 100,
                      color: Colors.blueAccent,
                      alignment: Alignment.center,
                      child: Text(
                        _operation,
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                    onTap: () => _updateText('Tap'),
                    onDoubleTap: () => _updateText('DoubleTap'),
                    onLongPress: () => _updateText('LongPress'),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  void _updateText(String text) {
    setState(() {
      _operation = text;
    });
  }
}
