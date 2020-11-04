import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class TurnBoxDemoRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _TurnBoxDemoRouteState();
}

class _TurnBoxDemoRouteState extends State<TurnBoxDemoRoute> {
  double _turns = .0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SafeArea(
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                TurnBoxDemo(turns: _turns, duration: 500, child: Icon(Icons.ac_unit, size: 50.0)),
                TurnBoxDemo(turns: _turns, duration: 500, child: Icon(Icons.ac_unit, size: 150.0)),
                RaisedButton(
                  child: Text('顺时针旋转1/5圈'),
                  onPressed: () {
                    setState(() {
                      _turns += .2;
                    });
                  },
                ),
                RaisedButton(
                  child: Text('逆时针旋转1/5圈'),
                  onPressed: () {
                    setState(() {
                      _turns -= .2;
                    });
                  },
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class TurnBoxDemo extends StatefulWidget {
  const TurnBoxDemo({
    Key key,
    this.turns,
    this.duration,
    this.child,
  }) : super(key: key);

  final double turns;
  final int duration;
  final Widget child;

  @override
  State<StatefulWidget> createState() => _TurnBoxDemoState();
}

class _TurnBoxDemoState extends State<TurnBoxDemo> with SingleTickerProviderStateMixin {
  AnimationController _animationController;

  @override
  void initState() {
    super.initState();

    _animationController = AnimationController(
      vsync: this,
      lowerBound: double.negativeInfinity,
      upperBound: double.infinity,
    );
    _animationController.value = widget.turns;
  }

  @override
  void didUpdateWidget(covariant TurnBoxDemo oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.turns == widget.turns) {
      return;
    }
    _animationController.animateTo(
      widget.turns,
      duration: Duration(milliseconds: widget.duration ?? 200),
      curve: Curves.easeOut,
    );
  }

  @override
  void dispose() {
    _animationController?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return RotationTransition(
      turns: _animationController,
      child: widget.child,
    );
  }
}
