import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class BothDirectionTestRoute extends StatefulWidget {
  const BothDirectionTestRoute({Key? key}) : super(key: key);

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
        child: SizedBox(
          width: double.infinity,
          height: double.infinity,
          child: Stack(
            children: <Widget>[
              Positioned(
                left: _left,
                top: _top,
                child: GestureDetector(
                  child: const CircleAvatar(child: Text('A')),
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
