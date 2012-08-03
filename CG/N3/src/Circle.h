#ifndef CIRCLE_H_
#define CIRCLE_H_

#include "ObjectGraph.h"
#include "Point.h"

class Circle : public ObjectGraph {
private:
	float radius;
	Point* pointInit;
public:
	Circle();
	~Circle();

	virtual void init();
	virtual void draw();
	virtual void nextPointHighlights();

};

#endif //CIRCLE_H_
