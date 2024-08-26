
# Not done with correct imports
# Some missing, some needs to be added
# Some are not in use...? Check flask imports please. Many are not needed
from flask import Flask, flash, redirect, render_template, request, session, abort, Response
from time import gmtime, strftime
from dotenv import load_dotenv
import os, pymysql.cursors, datetime, base64, requests


# Execute "database.sql" before using this
load_dotenv()
db = os.environ.get('db')


# Connect to MySQL database
connection = pymysql.connect(host="localhost",
								user="clocky_user",
								password=db,
								db="clocky",
								cursorclass=pymysql.cursors.DictCursor)

app = Flask(__name__)


# A new app will be deployed in prod soon
# Implement rate limiting on all endpoints
# Let's just use a WAF...?
# Not done (16/05-2023, jane)
@app.route("/")
def home():
	current_time = strftime("%Y-%m-%d %H:%M:%S", gmtime())
	return render_template("index.html", current_time=current_time)



# Done (16/05-2023, jane)
@app.route("/administrator", methods=["GET", "POST"])
def administrator():
	if session.get("logged_in"):
		return render_template("admin.html")

	else:
		if request.method == "GET":
			return render_template("login.html")
		
		if request.method == "POST":
			user_provided_username = request.form["username"]
			user_provided_password = request.form["password"]

			
			try:
				with connection.cursor() as cursor:

					sql = "SELECT ID FROM users WHERE username = %s"
					cursor.execute(sql, (user_provided_username))
					
					user_id = cursor.fetchone()
					user_id = user_id["ID"]

					sql = "SELECT password FROM passwords WHERE ID=%s AND password=%s"
					cursor.execute(sql, (user_id, user_provided_password))

					if cursor.fetchone():
						session["logged_in"] = True
						return redirect("/dashboard", code=302)

			except:
				pass
		
			message = "Invalid username or password"
			return render_template("login.html", message=message)

# Work in progress (10/05-2023, jane)
# Is the db really necessary?
@app.route("/forgot_password", methods=["GET", "POST"])
def forgot_password():
	if session.get("logged_in"):
		return render_template("admin.html")

	else:
		if request.method == "GET":
			return render_template("forgot_password.html")
		
		if request.method == "POST":
			username = request.form["username"]
			username = username.lower()

			try:
				with connection.cursor() as cursor:

					sql = "SELECT username FROM users WHERE username = %s"
					cursor.execute(sql, (username))

					if cursor.fetchone():
						value = datetime.datetime.now()
						lnk = str(value)[:-4] + " . " + username.upper()
						lnk = hashlib.sha1(lnk.encode("utf-8")).hexdigest()
						sql = "UPDATE reset_token SET token=%s WHERE username = %s"
						cursor.execute(sql, (lnk, username))
						connection.commit()

			except:
				pass

			message = "A reset link has been sent to your e-mail"
			return render_template("forgot_password.html", message=message)


# Done
@app.route("/password_reset", methods=["GET"])
def password_reset():
        if request.method == "GET":
                # Need to agree on the actual parameter here (12/05-2023, jane)
                if request.args.get("TEMPORARY"):
                        # Not done (11/05-2023, clarice)
                        # user_provided_token = request.args.get("TEMPORARY")

                        try:
                                with connection.cursor() as cursor:

                                        sql = "SELECT token FROM reset_token WHERE token = %s"
                                        cursor.execute(sql, (user_provided_token))
                                        if cursor.fetchone():
                                                return render_template("password_reset.html", token=user_provided_token)

                                        else:
                                                return "<h2>Invalid token</h2>"

                        except:
                                pass

                else:
                        return "<h2>Invalid parameter</h2>"
        return "<h2>Invalid parameter</h2>"



# Debug enabled during dev
# TURN OFF ONCE IN PROD!
# This can be very dangerous
# ref https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/werkzeug#pin-protected-path-traversal

# Use gunicorn?
if __name__ == "__main__":
	app.secret_key = os.urandom(256)
	app.run(host="0.0.0.0", port="8080", debug=True)

