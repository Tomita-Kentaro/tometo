import sqlite3
con = sqlite3.connect('TestDB.db')
#con.execute("CREATE TABLE members(id INTEGER PRIMARY KEY, name STRING, age INTEGER, hobby STRING)")
#con.execute("INSERT INTO members(id, name, age, hobby) values(1, 'kentaro', '25', 'guitar')")
con.commit()
cur = con.execute("SELECT name FROM members")
for row in cur:
    data = row[0]
print(data)
con.execute("DELETE FROM members")
con.execute("DROP TABLE members")
con.commit()
con.close()