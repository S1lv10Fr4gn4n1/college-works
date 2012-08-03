#ifndef POINT_H_
#define POINT_H_

class Point {
private:
	float x, y, z, w;
public:
	Point();
	Point(float _x, float _y, float _z, float _w);
	Point(float _x, float _y);
	~Point();

	float getX();
	void setX(float _x);
	float getY();
	void setY(float _y);
	float getZ();
	void setZ(float _z);
	float getW();
	void setW(float _w);
};

#endif //POINT_H_
