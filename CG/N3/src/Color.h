#ifndef COLOR_H_
#define COLOR_H_

class Color {
private:
	unsigned char r;
	unsigned char g;
	unsigned char b;
public:
	Color();
	Color(unsigned char _r, unsigned char _g, unsigned char _b);
	~Color();

	unsigned char getR();
	void setR(unsigned char _r);
	unsigned char getG();
	void setG(unsigned char _g);
	unsigned char getB();
	void setB(unsigned char _b);
};

#endif //COLOR_H_
