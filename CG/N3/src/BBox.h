#ifndef BBOX_H_
#define BBOX_H_

#include "Point.h"
#include "Transform.h"

class BBox {
private:
	float xMin;
	float xMax;

	float yMin;
	float yMax;

	float zMin;
	float zMax;
public:
	BBox();
	~BBox();

	void init();

	bool inBBox(float _x, float _y, float _z);
	bool inBBox(Point* _point);

	float getXMin();
	void setXMin(float _x);
	float getXMax();
	void setXMax(float _x);

	float getYMin();
	void setYMin(float _y);
	float getYMax();
	void setYMax(float _y);

	float getZMin();
	void setZMin(float _z);
	float getZMax();
	void setZMax(float _z);
};

#endif //BBOX
