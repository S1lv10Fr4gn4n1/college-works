#ifndef WORLD_H_
#define WORLD_H_

#include "ObjectGraph.h"
#include <vector>

class World {
private:
	std::vector<ObjectGraph*> objsGraph;

	int width;
	int height;

	double left;
	double right;
	double top;
	double bottom;

	// utilizados para o PAN e zoom, fator de incremento
	float incLeft;
	float incRight;
	float incTop;
	float incBottom;
public:
	World();
	~World();

	std::vector<ObjectGraph*> getObjsGragh();
	void addObjsGraph(ObjectGraph* obj);

	int getWidth();
	void setWidth(int _width);
	int getHeight();
	void setHeight(int _height);

	double getLeft();
	void setLeft(double _left);
	double getRight();
	void setRight(double _right);
	double getTop();
	void setTop(double _top);
	double getBottom();
	void setBottom(double _bottom);

	float getIncLeft();
	void setIncLeft(float _incLeft);
	float getIncRight();
	void setIncRight(float _incRight);
	float getIncTop();
	void setIncTop(float _incTop);
	float getIncBottom();
	void setIncBottom(float _incBottom);

	void northPAN();
	void westPAN();
	void southPAN();
	void eastPAN();
	void zoomIn();
	void zoomOut();

	void deleteAllObjects();
	void deleteObject(ObjectGraph* _obj);
	void recalculateWorldDimension(int _width, int _heigth);
};

#endif //WORLD_H_
