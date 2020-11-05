import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class StudyDrag extends StatelessWidget {
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
                _Drag(),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class _Drag extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _DragState();
}

class _DragState extends State<_Drag> {
  double _left = 0.0;
  double _top = 0.0;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: double.infinity,
      height: 300,
      child: Stack(
        children: [
          Positioned(
            left: _left,
            top: _top,
            child: GestureDetector(
              child: CircleAvatar(child: Text('A')),
              onPanUpdate: (DragUpdateDetails details) {
                setState(() {
                  _left += details.delta.dx;
                  _top += details.delta.dy;
                });
              },
            ),
          ),
        ],
      ),
    );
  }
}
