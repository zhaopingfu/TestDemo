import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class TurnBox extends StatefulWidget {
  const TurnBox({
    Key? key,
    this.turns = .0, // 旋转的“圈”数,一圈为360度，如0.25圈即90度
    this.speed = 200, // 过渡动画执行的总时长
    required this.child,
  }) : super(key: key);

  final double turns;
  final int speed;
  final Widget child;

  @override
  State<StatefulWidget> createState() => _TurnBoxState();
}

class _TurnBoxState extends State<TurnBox> with SingleTickerProviderStateMixin {
  late AnimationController _animationController;

  @override
  void initState() {
    super.initState();
    _animationController = AnimationController(vsync: this, lowerBound: -double.infinity, upperBound: double.infinity);
    _animationController.value = widget.turns;
  }

  @override
  Widget build(BuildContext context) {
    return RotationTransition(
      turns: _animationController,
      child: widget.child,
    );
  }

  @override
  void didUpdateWidget(covariant TurnBox oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.turns == widget.turns) {
      return;
    }
    _animationController.animateTo(
      widget.turns,
      duration: Duration(milliseconds: widget.speed),
      curve: Curves.easeOut,
    );
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }
}

class TurnBoxRoute extends StatefulWidget {
  const TurnBoxRoute({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _TurnBoxRouteState();
}

class _TurnBoxRouteState extends State<TurnBoxRoute> {
  double _turns = .0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: SafeArea(
          child: Column(
            children: <Widget>[
              TurnBox(turns: _turns, speed: 500, child: const Icon(Icons.ac_unit, size: 50.0)),
              TurnBox(turns: _turns, speed: 1000, child: const Icon(Icons.ac_unit, size: 150.0)),
              ElevatedButton(
                child: const Text('顺时针旋转1/5圈'),
                onPressed: () {
                  setState(() {
                    _turns += .2;
                  });
                },
              ),
              ElevatedButton(
                child: const Text('逆时针旋转1/5圈'),
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
    );
  }
}
