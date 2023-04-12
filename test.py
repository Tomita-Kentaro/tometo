import pandas as pd
f = pd.read_csv("./name.txt", sep=" ")
f.to_csv("./new_name.csv", index=None)