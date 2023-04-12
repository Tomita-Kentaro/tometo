from flask import Flask
from flask import request, make_response, jsonify
from flask_httpauth import HTTPBasicAuth
from flask_cors import CORS
import sqlite3
import webbrowser

app = Flask(__name__, static_folder="./build/static", template_folder="./build")
auth = HTTPBasicAuth()
CORS(app, supports_credentials=True)

users = {
    "a":"1",
    "b":"2",
    "c":"3"
}

@auth.get_password
def get_pw(username):
    if username in users:
        return users.get(username)
    return None

@app.route('/')
@auth.login_required
def index():
    webbrowser.open('http://127.0.0.1:3000/')
    con = sqlite3.connect('TodoDB.db', detect_types=sqlite3.PARSE_DECLTYPES)
    con.execute("DROP TABLE user")
    con.execute("CREATE TABLE user(id TEXT)")
    con.execute("INSERT INTO user(id) values(:id)", {"id": auth.username()})
    con.commit()
    return "こんにちは、%sさん。このページは閉じてください。 " % auth.username()

@app.route('/reload', methods=['GET', 'POST'])
def reload():
    con = sqlite3.connect('TodoDB.db', detect_types=sqlite3.PARSE_DECLTYPES)
    cur = con.execute("SELECT * FROM user")
    login_user = str(cur.fetchone()[0])
    print(login_user)
    if login_user == 'a':
        cur = con.execute("SELECT * FROM a")
        db_col = ['id', 'name', 'completed']
        text = cur.fetchall()
        data_list = []
        for str_data in text:
            data = dict(zip(db_col, str_data))
            data_list.append(data)
        response = {'result': data_list}
        print(response)
        return make_response(jsonify(response))
    elif login_user == 'b':
        cur = con.execute("SELECT * FROM b")
        db_col = ['id', 'name', 'completed']
        text = cur.fetchall()
        data_list = []
        for str_data in text:
            data = dict(zip(db_col, str_data))
            data_list.append(data)
        response = {'result': data_list}
        print(response)
        return make_response(jsonify(response))
    elif login_user == 'c':
        cur = con.execute("SELECT * FROM c")
        db_col = ['id', 'name', 'completed']
        text = cur.fetchall()
        data_list = []
        for str_data in text:
            data = dict(zip(db_col, str_data))
            data_list.append(data)
        response = {'result': data_list}
        print(response)
        return make_response(jsonify(response))
    else:
        text = []
        response = {'result': text}
        return make_response(jsonify(response))

@app.route('/post', methods=['GET', 'POST'])
def parse():
    data = request.get_json()
    con = sqlite3.connect('TodoDB.db', detect_types=sqlite3.PARSE_DECLTYPES)
    cur = con.execute("SELECT * FROM user")
    login_user = str(cur.fetchone()[0])
    print(login_user)
    if login_user == 'a':
        cur = con.execute("SELECT COUNT(*) FROM sqlite_master WHERE TYPE='table' AND name='a'")
        table_a = int(cur.fetchone()[0])
        print(table_a)
        if (table_a == 1):
            con.execute("DROP TABLE a")
        con.execute("CREATE TABLE a(id TEXT PRIMARY KEY, name TEXT, completed INTEGER)")
        text = data['post_text']
        print(text)
        for str_data in text:
            id_data = str_data['id']
            name = str_data['name']
            completed = str_data['completed']
            if completed == False:
                completed_data = 0
            else:
                completed_data = 1
            con.execute("INSERT INTO a(id, name, completed) values(:id, :name, :completed)", {"id": id_data, "name": name, "completed": completed_data})
            con.commit()
        response = {'result': text}
        print(response)
        return make_response(jsonify(response))
    elif login_user == 'b':
        cur = con.execute("SELECT COUNT(*) FROM sqlite_master WHERE TYPE='table' AND name='b'")
        table_b = int(cur.fetchone()[0])
        print(table_b)
        if (table_b == 1):
            con.execute("DROP TABLE b")
        con.execute("CREATE TABLE b(id TEXT PRIMARY KEY, name TEXT, completed INTEGER)")
        text = data['post_text']
        print(text)
        for str_data in text:
            id_data = str_data['id']
            name = str_data['name']
            completed = str_data['completed']
            if completed == False:
                completed_data = 0
            else:
                completed_data = 1
            con.execute("INSERT INTO b(id, name, completed) values(:id, :name, :completed)", {"id": id_data, "name": name, "completed": completed_data})
            con.commit()
        response = {'result': text}
        print(response)
        return make_response(jsonify(response))
    elif login_user == 'c':
        cur = con.execute("SELECT COUNT(*) FROM sqlite_master WHERE TYPE='table' AND name='c'")
        table_c = int(cur.fetchone()[0])
        print(table_c)
        if (table_c == 1):
            con.execute("DROP TABLE c")
        con.execute("CREATE TABLE c(id TEXT PRIMARY KEY, name TEXT, completed INTEGER)")
        text = data['post_text']
        print(text)
        for str_data in text:
            id_data = str_data['id']
            name = str_data['name']
            completed = str_data['completed']
            if completed == False:
                completed_data = 0
            else:
                completed_data = 1
            con.execute("INSERT INTO c(id, name, completed) values(:id, :name, :completed)", {"id": id_data, "name": name, "completed": completed_data})
            con.commit()
        response = {'result': text}
        print(response)
        return make_response(jsonify(response))
    else:
        text = []
        response = {'result': text}
        return make_response(jsonify(response))

        
if __name__=='__main__':
    app.run(debug=True, port=5000, threaded=True)