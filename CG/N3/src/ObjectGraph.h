#ifndef OBJECTGRAPH_H_
#define OBJECTGRAPH_H_

#include <vector>
#include "BBox.h"
#include "Point.h"
#include "Color.h"
#include "Transform.h"
#include "TypeTransform.h"

class ObjectGraph {
private:
	Color* color;
	Transform* transform;
	bool modeEdition;
	bool showBbox;
protected:
	BBox* bbox;
	std::vector<Point*> points;
	void drawColor();
	int indexHighlights;
public:
	ObjectGraph();
	~ObjectGraph();

	virtual void init();
	virtual void draw();
	virtual void addPoint(Point* _point);
	virtual std::vector<Point*> getPoints();
	virtual void initBBox(Transform* _t);

	void setTransform(Transform* _t);
	Transform* getTransform();
	void setColor(Color* _color);
	Color* getColor();
	void setTypeTranform(TypeTransform* _type);
	TypeTransform* getTypeTransform();
	BBox* getBBox();

	void setModeEdition(bool _modeEdition);
	bool getModeEdition();

	virtual void nextPointHighlights();
	void setPointHighlights(Point* _point);
	Point* getPointHighlights();
	void savePointHighlights();
	void deleteActualPoint();
	void showBBox();
};

#endif //OBJECTGRAPH_H_
