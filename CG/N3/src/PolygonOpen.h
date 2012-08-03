#ifndef POLYGONOPEN_H_
#define POLYGONOPEN_H_

#include "ObjectGraph.h"

class PolygonOpen : public ObjectGraph {
private:
public:
	PolygonOpen();
	~PolygonOpen();

	virtual void init();
	virtual void draw();
};

#endif //POLYGONOPEN_H_
