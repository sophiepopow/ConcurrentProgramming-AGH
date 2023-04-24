import matplotlib.pyplot as plt
data = []
while True:
    o = input()
    if o == "end":
        break
    data.append(o.split(" "))

JFC = {}
JFA = {}

for d in data:
    if d[0] == "JFA":
        if not JFA.get(d[1]):
            JFA[d[1]] = d[2]
    if d[0] == "JFC":
        if not JFC.get(d[1]):
            JFC[d[1]] = d[2]

def calculate_x_y(obj):
    x = []
    y = []
    res = []
    for attr, value in obj.items():
        x.append(int(attr))
        y.append(int(value))
        print(attr, value)

    return x,y

xJFA, yJFA  = calculate_x_y(JFA)
xJFC, yJFC  = calculate_x_y(JFC)

plt.title("Jedzący filozofowie red-asymetrycznie, blue-kelner, liczba filozfow 0-1000")
plt.plot(xJFA, yJFA,'ro',markersize=1)
plt.plot(xJFC, yJFC,'bo',markersize=1)
color='green'
plt.show()

plt.title("Jedzący filozofowie asymetryczni")
plt.plot(xJFA, yJFA,'ro',markersize=1)
color='green'
plt.show()

plt.title("Jedzący filozofowie kelner")
plt.plot(xJFC, yJFC,'bo',markersize=1)
color='green'
plt.show()
