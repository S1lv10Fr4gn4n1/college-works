#ifndef POLYGONCLOSE_H_
#define POLYGONCLOSE_H_

#include "ObjectGraph.h"

class PolygonClose : public ObjectGraph {
private:
public:
	PolygonClose();
	~PolygonClose();

	virtual void init();
	virtual void draw();
};

#endif //POLYGONCLOSE_H_
