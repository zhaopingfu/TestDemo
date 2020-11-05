import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BothDirectionTestRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _BothDirectionTestRouteState();
}

class _BothDirectionTestRouteState extends State<BothDirectionTestRoute> {
  double _left = .0;
  double _top = .0;

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
                top: _top,
                child: GestureDetector(
                  child: CircleAvatar(child: Text('A')),
                  onHorizontalDragUpdate: (DragUpdateDetails details) => setState(() => _left += details.delta.dx),
                  onVerticalDragUpdate: (DragUpdateDetails details) => setState(() => _top += details.delta.dy),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
