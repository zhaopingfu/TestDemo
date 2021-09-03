import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class GestureConflictTestRoute extends StatefulWidget {
  const GestureConflictTestRoute({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _GestureConflictTestRouteState();
}

class _GestureConflictTestRouteState extends State<GestureConflictTestRoute> {
  double _left = .0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: SizedBox(
          width: double.infinity,
          height: double.infinity,
          child: Stack(
            children: <Widget>[
              Positioned(
                left: _left,
                child: Listener(
                  child: GestureDetector(
                    child: const CircleAvatar(child: Text('A')),
                    onHorizontalDragUpdate: (DragUpdateDetails detail) => setState(() => _left += detail.delta.dx),
                    onHorizontalDragEnd: (DragEndDetails detail) => print('drag end'),
                  ),
                  onPointerDown: (PointerDownEvent detail) => print('down'),
                  onPointerUp: (PointerUpEvent detail) => print('up'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
