#ifndef SPLINE_H_
#define SPLINE_H_

#include <iostream>
#include <vector>
#include "ObjectGraph.h"
#include "Point.h"

class Spline : public ObjectGraph {
private:
	int initialized;
	std::vector<Point*> pointsReference;
public:
	Spline();
	~Spline();

	virtual void init();
	virtual void draw();
	virtual void nextPointHighlights();

	void paintReferences();
};

#endif // SPLINE_H_
