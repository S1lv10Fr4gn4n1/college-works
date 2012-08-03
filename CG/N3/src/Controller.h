#ifndef CONTROLLER_H_
#define CONTROLLER_H_

#include <cmath>
#include "Point.h"
#include "World.h"

class Controller {
private:
	World* world;
	ObjectGraph* currentObjectGraph;
	int maxPoints;
	int totalPoints;

	bool modeEdition;

	void displayMenu();
	Color* getRandomColor();
	void finalizeConstructionObject();
	void finalizeEditionObject();
	void newGraphObject(ObjectGraph* _obj, int _maxPoints);

	Point* ndc(float _x, float _y);
	void selectObject(Point* _point);
	bool inBBox(ObjectGraph* _object, Point* _point);
	bool inObject(ObjectGraph* _object, Point* _point);
	void deleteObjetct();
	void deletePoint();
	void cleanAllObjects();
	void nextPointInObject();
	void setMovingPoint(int _x, int _y);
	void showBBox();
public:
	Controller();
	~Controller();

	void drawAllObj();

	void reshape(int _width, int _heigth);
	void keyboard(unsigned char _key, int _x, int _y);
	void special(int _key, int _x, int _y);
	void motion(int _x, int _y);
	void mouse(int _button, int _state, int _x, int _y);
	void passive(int _x, int _y);

	double getOrtho2DLeft();
	double getOrtho2DRight();
	double getOrtho2DBottom();
	double getOrtho2DTop();
};

#endif //CONTROLLER_H_
