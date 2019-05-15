import sys
import numpy as np
import matplotlib.pyplot as plt
import csv
from matplotlib.animation import FuncAnimation

fig, ax = plt.subplots()
fig.set_tight_layout(True)

x1 = []
y1 = []

xdata, ydata = [10], [10]
x2 = []
y2 = []

p = 0

with open(sys.argv[1], 'r') as csvfile:
    plots = csv.reader(csvfile, delimiter=';')
    for row in plots:
        x1.append(float(row[0]))
        y1.append(float(row[1]))
plt.plot(x1, y1, 'ko')

with open(sys.argv[2], 'r') as csvfile:
    plots = csv.reader(csvfile, delimiter=';')
    for row in plots:
        x2.append(float(row[0]))
        y2.append(float(row[1]))
ln, = plt.plot([], [], 'ro')



def animate(i):

    global p
    del xdata[:]
    del ydata[:]
    for x in xrange(p, p + 10):
        xdata.append(x2[x])
        ydata.append(y2[x])


    print(xdata)
    if len(x2) > p + 1 + 10:
        p = p + 10
    else:
        p = 0
    ln.set_data(xdata, ydata)
    return ln,

if __name__ == '__main__':
    ax.axis([-10,10,-10,10])
    ani = FuncAnimation(fig, animate, interval=10, frames=20)
    ani.save("output.gif", writer="imagemagick")
    plt.show()
