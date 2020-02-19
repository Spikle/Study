import sys; sys.path
import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from matplotlib import cm
from mpl_toolkits.mplot3d import Axes3D

PLOT_LABEL_FONT_SIZE = 12 

def getColors(n):
    COLORS = []
    cm = plt.cm.get_cmap('hsv', n)
    for i in np.arange(n):
        COLORS.append(cm(i))
    return COLORS

def dict_sort(my_dict):
    keys = []
    values = []
    my_dict = sorted(my_dict.items(), key=lambda x:x[1], reverse=True)
    for k, v in my_dict:
        keys.append(k)
        values.append(v)
    return (keys,values)

data = pd.read_csv('./vgsales.csv', escapechar='`', low_memory=False)
data.head()
data.dropna(how ='any', inplace = True)
total = data.isnull().sum().sort_values(ascending=False)
percent = (data.isnull().sum()/data.isnull().count()).sort_values(ascending=False)
missing_data = pd.concat([total, percent], axis=1, keys=['Total', 'Percent'])
missing_data.head(6)

g = sns.countplot(x="Platform",data=data, palette = "Set1")
g.set_xticklabels(g.get_xticklabels(), rotation=90, ha="right")
plt.title('По популярности платформ',size = 14)
plt.tight_layout()
plt.show();

g = sns.countplot(x="Genre",data=data, palette = "Set1")
g.set_xticklabels(g.get_xticklabels(), rotation=90, ha="right")
plt.title('По жанру',size = 14)
plt.tight_layout()
plt.show();

g = sns.catplot(x="Genre",y="Global_Sales",data=data, kind="box", height = 10 ,palette = "Set1")
g.despine(left=True)
g.set_xticklabels(rotation=90)
g = g.set_ylabels("Объем продаж")
plt.title('Зависимость объем продаж от жанра',size = 20)
plt.tight_layout()
plt.show();

g = sns.catplot(x="Platform",y="Global_Sales",data=data, kind="box", height = 10 ,palette = "Set1")
g.despine(left=True)
g.set_xticklabels(rotation=90)
g = g.set_ylabels("Объем продаж")
plt.title('Зависимость объем продаж от платформы',size = 20)
plt.tight_layout()
plt.show();


