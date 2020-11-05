import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class StudyScaleRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _StudyScaleRouteState();
}

class _StudyScaleRouteState extends State<StudyScaleRoute> {
  double _width = 200.0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          width: double.infinity,
          height: double.infinity,
          child: Center(
            child: GestureDetector(
              child: Image.asset('assets/test_scale.jpg', width: _width),
              onScaleUpdate: (ScaleUpdateDetails details) {
                setState(() {
                  // 缩放倍数在0.8到10倍之间
                  _width = 200 * details.scale.clamp(.8, 10);
                });
              },
            ),
          ),
        ),
      ),
    );
  }
}
