#ifndef TRANSFORM_H_
#define TRANSFORM_H_

#include <iostream>
#include "Point.h"
#include "TypeTransform.h"

class Transform {
private:
	float matrix[16];

	TypeTransform typeTransform;

	Transform* transformMatrix(Transform* _t);

	void translate(float _x, float _y, float _z);
	void scale(float _x, float _y, float _z);
	void rotate(float _x, float _y, float _z);
public:
	Transform();
	~Transform();

	Point* transformPoint(Point* _point);
	void makeIdentity();
	void transformGraphObject(float _x, float _y, float _z);
	void printMatrix(std::string _desc);

	float* getMatrix();
	float getElement(int _i);
	void setElement(int _i, float _value);
	void setTypeTransform(TypeTransform _type);
};

#endif // TRANSFORM_H_
