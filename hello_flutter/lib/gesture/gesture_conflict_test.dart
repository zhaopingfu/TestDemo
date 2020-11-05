import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class GestureConflictTestRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _GestureConflictTestRouteState();
}

class _GestureConflictTestRouteState extends State<GestureConflictTestRoute> {
  double _left = .0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          width: double.infinity,
          height: double.infinity,
          child: Stack(
            children: [
              Positioned(
                left: _left,
                child: Listener(
                  child: GestureDetector(
                    child: CircleAvatar(child: Text('A')),
                    onHorizontalDragUpdate: (detail) => setState(() => _left += detail.delta.dx),
                    onHorizontalDragEnd: (detail) => print('drag end'),
                  ),
                  onPointerDown: (detail) => print('down'),
                  onPointerUp: (detail) => print('up'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
