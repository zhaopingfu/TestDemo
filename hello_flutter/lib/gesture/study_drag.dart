import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class StudyDrag extends StatelessWidget {
  const StudyDrag({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SizedBox(
          width: double.infinity,
          height: double.infinity,
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[_Drag()],
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
        children: <Widget>[
          Positioned(
            left: _left,
            top: _top,
            child: GestureDetector(
              child: const CircleAvatar(child: Text('A')),
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
