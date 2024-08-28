#!/usr/bin/env python
# encoding: UTF-8

"""
This file is part of Commix Project (https://commixproject.com).
Copyright (c) 2014-2024 Anastasios Stasinopoulos (@ancst).

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

For more see the file 'readme/COPYING' for copying permission.
"""

import os
import sys
import time
import base64
import sqlite3
from src.utils import menu
from src.utils import settings
from src.utils import common
from src.core.injections.controller import checks
from src.thirdparty.six.moves import input as _input
from src.thirdparty.colorama import Fore, Back, Style, init

"""
Session handler via SQLite3 db.
"""
no_such_table = False

"""
Generate table name for SQLite3 db.
"""
def table_name(url):
  host = url.split('//', 1)[1].split('/', 1)[0]
  table_name = "session_" + host.replace(".","_").replace(":","_").replace("-","_").replace("[","_").replace("]","_")
  return table_name

"""
Ignore session.
"""
def ignore(url):
  if os.path.isfile(settings.SESSION_FILE):
    if settings.VERBOSITY_LEVEL != 0:
      debug_msg = "Ignoring the stored session from the session file due to '--ignore-session' switch."
      settings.print_data_to_stdout(settings.print_debug_msg(debug_msg))
  else:
    if settings.VERBOSITY_LEVEL != 0:
      warn_msg = "Skipping ignoring the stored session, as the session file not exist."
      settings.print_data_to_stdout(settings.print_warning_msg(warn_msg))

"""
Flush session.
"""
def flush(url):
  if os.path.isfile(settings.SESSION_FILE):
    if settings.VERBOSITY_LEVEL != 0:
      debug_msg = "Flushing the stored session from the session file."
      settings.print_data_to_stdout(settings.print_debug_msg(debug_msg))
    try:
      conn = sqlite3.connect(settings.SESSION_FILE)
      tables = list(conn.execute("SELECT name FROM sqlite_master WHERE type is 'table'"))
      conn.executescript(';'.join(["DROP TABLE IF EXISTS %s" %i for i in tables]))
      conn.commit()
      conn.close()
    except sqlite3.OperationalError as err_msg:
      settings.print_data_to_stdout(settings.SINGLE_WHITESPACE)
      err_msg = "Unable to flush the session file." + str(err_msg).title()
      settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
  else:
    if settings.VERBOSITY_LEVEL != 0:
      warn_msg = "Skipping flushing the stored session, as the session file not exist."
      settings.print_data_to_stdout(settings.print_warning_msg(warn_msg))

"""
Clear injection point records
except latest for every technique.
"""
def clear(url):
  try:
    if no_such_table:
      conn = sqlite3.connect(settings.SESSION_FILE)
      conn.execute("DELETE FROM " + table_name(url) + "_ip WHERE "\
                   "id NOT IN (SELECT MAX(id) FROM " + \
                   table_name(url) + "_ip GROUP BY technique);")
      conn.commit()
      conn.close()
  except sqlite3.OperationalError as err_msg:
    settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
  except:
    settings.LOAD_SESSION = False
    return False

"""
Import successful injection points to session file.
"""
def injection_point_importation(url, technique, injection_type, separator, shell, vuln_parameter, prefix, suffix, TAG, alter_shell, payload, http_request_method, url_time_response, timesec, exec_time, output_length, is_vulnerable):
  try:
    conn = sqlite3.connect(settings.SESSION_FILE)
    conn.execute("CREATE TABLE IF NOT EXISTS " + table_name(url) + "_ip" + \
                 "(id INTEGER PRIMARY KEY, url VARCHAR, technique VARCHAR, injection_type VARCHAR, separator VARCHAR," \
                 "shell VARCHAR, vuln_parameter VARCHAR, prefix VARCHAR, suffix VARCHAR, "\
                 "TAG VARCHAR, alter_shell VARCHAR, payload VARCHAR, http_header VARCHAR, http_request_method VARCHAR, url_time_response INTEGER, "\
                 "timesec INTEGER, exec_time INTEGER, output_length INTEGER, is_vulnerable VARCHAR);")

    conn.execute("INSERT INTO " + table_name(url) + "_ip(url, technique, injection_type, separator, "\
                 "shell, vuln_parameter, prefix, suffix, TAG, alter_shell, payload, http_header, http_request_method, "\
                 "url_time_response, timesec, exec_time, output_length, is_vulnerable) "\
                 "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", \
                 (str(url), str(technique), str(injection_type), \
                 str(separator), str(shell), str(vuln_parameter), str(prefix), str(suffix), \
                 str(TAG), str(alter_shell), str(payload), str(settings.HTTP_HEADER), str(http_request_method), \
                 int(url_time_response), int(timesec), int(exec_time), \
                 int(output_length), str(is_vulnerable)))
    conn.commit()
    conn.close()
    if settings.INJECTION_CHECKER == False:
      settings.INJECTION_CHECKER = True

  except sqlite3.OperationalError as err_msg:
    err_msg = str(err_msg)[:1].upper() + str(err_msg)[1:] + "."
    err_msg += " You are advised to rerun with switch '--flush-session'."
    settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
    raise SystemExit()

  except sqlite3.DatabaseError as err_msg:
    checks.error_loading_session_file()

"""
Export successful applied techniques from session file.
"""
def applied_techniques(url, http_request_method):
  try:
    conn = sqlite3.connect(settings.SESSION_FILE)
    if not menu.options.tech:
      applied_techniques = conn.execute("SELECT technique FROM " + table_name(url) + "_ip "\
                                        "ORDER BY id DESC;")

    if settings.TESTABLE_PARAMETER:
      applied_techniques = conn.execute("SELECT technique FROM " + table_name(url) + "_ip WHERE "\
                                        "url = '" + url + "' AND "\
                                        "vuln_parameter = '" + settings.TESTABLE_PARAMETER + "' AND "\
                                        "http_request_method = '" + http_request_method + "' "\
                                        "ORDER BY id DESC ;")
    else:
      applied_techniques = conn.execute("SELECT technique FROM " + table_name(url) + "_ip WHERE "\
                                        "url = '" + url + "' AND "\
                                        "vuln_parameter = '" + settings.INJECT_TAG + "' AND "\
                                        "http_request_method = '" + http_request_method + "' "\
                                        "ORDER BY id DESC ;")
    values = []
    for session in applied_techniques:
      if "tempfile" in session[0][:8]:
        settings.TEMPFILE_BASED_STATE = True
        session = session[0][4:]
      elif "dynamic" in session[0][:7]:
        settings.EVAL_BASED_STATE = True
        session = session[0][13:]
      values += session[0][:1]
    applied_techniques = ''.join(list(set(values)))
    return applied_techniques
  except sqlite3.OperationalError as err_msg:
    #settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
    settings.LOAD_SESSION = False
    return False
  except:
    settings.LOAD_SESSION = False
    return False

"""
Export successful applied techniques from session file.
"""
def applied_levels(url, http_request_method):
  try:
    conn = sqlite3.connect(settings.SESSION_FILE)
    if settings.TESTABLE_PARAMETER:
      applied_level = conn.execute("SELECT is_vulnerable FROM " + table_name(url) + "_ip WHERE "\
                                    "url = '" + url + "' AND "\
                                    "vuln_parameter = '" + settings.TESTABLE_PARAMETER + "' AND "\
                                    "http_request_method = '" + http_request_method + "' "\
                                    "ORDER BY id DESC;")
    else:
      applied_level = conn.execute("SELECT is_vulnerable FROM " + table_name(url) + "_ip WHERE "\
                                    "url = '" + url + "' AND "\
                                    "vuln_parameter = '" + settings.INJECT_TAG + "' AND "\
                                    "http_request_method = '" + http_request_method + "' "\
                                    "ORDER BY id DESC;")

    for session in applied_level:
      return session[0]

  except sqlite3.OperationalError as err_msg:
    #settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
    settings.LOAD_SESSION = False
    return False
  except:
    settings.LOAD_SESSION = False
    return False

"""
Export successful injection points from session file.
"""
def injection_point_exportation(url, http_request_method):
  try:
    if not menu.options.flush_session:
      conn = sqlite3.connect(settings.SESSION_FILE)
      result = conn.execute("SELECT * FROM sqlite_master WHERE name = '" + \
                             table_name(url) + "_ip' AND type = 'table';")
      if result:
        # if menu.options.tech[:1] == "c":
        #   select_injection_type = "R"
        # elif menu.options.tech[:1] == "e":
        #   settings.EVAL_BASED_STATE = True
        #   select_injection_type = "R"
        # elif menu.options.tech[:1] == "t":
        #   select_injection_type = "B"
        # else:
        #   select_injection_type = "S"
        # if settings.TEMPFILE_BASED_STATE and select_injection_type == "S":
        #   check_injection_technique = "t"
        # elif settings.EVAL_BASED_STATE and select_injection_type == "R":
        #   check_injection_technique = "d"
        # else:
        #   check_injection_technique = menu.options.tech[:1]
        if settings.TESTABLE_PARAMETER:
          cursor = conn.execute("SELECT * FROM " + table_name(url) + "_ip WHERE "\
                                "url = '" + url + "' AND "\
                                # "injection_type like '" + select_injection_type + "%' AND "\
                                # "technique like '" + check_injection_technique + "%' AND "\
                                "vuln_parameter = '" + settings.TESTABLE_PARAMETER + "' AND "\
                                "http_request_method = '" + http_request_method + "' "\
                                "ORDER BY id DESC limit 1;")

        else:
          cursor = conn.execute("SELECT * FROM " + table_name(url) + "_ip WHERE "\
                                "url = '" + url + "' AND "\
                                # "injection_type like '" + select_injection_type + "%' AND "\
                                # "technique like '" + check_injection_technique + "%' AND "\
                                "http_header = '" + settings.HTTP_HEADER + "' AND "\
                                "http_request_method = '" + http_request_method + "' "\
                                "ORDER BY id DESC limit 1;")

        for session in cursor:
          url = session[1]
          technique = session[2]
          injection_type = session[3]
          separator = session[4]
          shell = session[5]
          vuln_parameter = session[6]
          prefix = session[7]
          suffix = session[8]
          TAG = session[9]
          alter_shell = session[10]
          payload = session[11]
          http_request_method = session[13]
          url_time_response = session[14]
          timesec = session[15]
          exec_time = session[16]
          output_length = session[17]
          is_vulnerable = session[18]
          return url, technique, injection_type, separator, shell, vuln_parameter, prefix, suffix, TAG, alter_shell, payload, http_request_method, url_time_response, timesec, exec_time, output_length, is_vulnerable
    else:
      no_such_table = True
      pass
  except sqlite3.OperationalError as err_msg:
    #settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
    settings.LOAD_SESSION = False
    return False
  except:
    settings.LOAD_SESSION = False
    return False

"""
Notification about session.
"""
def notification(url, technique, injection_type):
  try:
    if settings.LOAD_SESSION == True:
      while True:
        # message = "A previously stored session has been held against that target. "
        message = "Do you want to resume to the "
        message += "(" + injection_type.split(settings.SINGLE_WHITESPACE)[0] + ") "
        message += technique.rsplit(' ', 2)[0]
        message += " injection point from stored session? [Y/n] > "
        settings.LOAD_SESSION = common.read_input(message, default="Y", check_batch=True)
        if settings.LOAD_SESSION in settings.CHOICE_YES:
          settings.INJECTION_CHECKER = True
          return True
        elif settings.LOAD_SESSION in settings.CHOICE_NO:
          settings.LOAD_SESSION = False
          settings.RESET_TESTS = True
          return False
        elif settings.LOAD_SESSION in settings.CHOICE_QUIT:
          raise SystemExit()
        else:
          common.invalid_option(settings.LOAD_SESSION)
          pass
  except sqlite3.OperationalError as err_msg:
    settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
  except (KeyboardInterrupt, SystemExit):
    raise

"""
Check for specific stored parameter.
"""
def check_stored_parameter(url, http_request_method):
  if injection_point_exportation(url, http_request_method):
    if injection_point_exportation(url, http_request_method)[16] == str(menu.options.level):
      # Check for stored alternative shell
      if injection_point_exportation(url, http_request_method)[9] != "":
        menu.options.alter_shell = injection_point_exportation(url, http_request_method)[9]
      return True
    else:
      return False
  else:
    return False

"""
Import successful command execution outputs to session file.
"""
def store_cmd(url, cmd, shell, vuln_parameter):
  if any(type(_) is str for _ in (url, cmd, shell, vuln_parameter)):
    try:
      conn = sqlite3.connect(settings.SESSION_FILE)
      conn.execute("CREATE TABLE IF NOT EXISTS " + table_name(url) + "_ir" + \
                   "(cmd VARCHAR, output VARCHAR, vuln_parameter VARCHAR);")
      if settings.TESTABLE_PARAMETER:
        conn.execute("INSERT INTO " + table_name(url) + "_ir(cmd, output, vuln_parameter) " \
                     "VALUES(?,?,?)", \
                     (str(base64.b64encode(cmd.encode(settings.DEFAULT_CODEC)).decode()), \
                      str(base64.b64encode(shell.encode(settings.DEFAULT_CODEC)).decode()), \
                      str(vuln_parameter)))
      else:
        conn.execute("INSERT INTO " + table_name(url) + "_ir(cmd, output, vuln_parameter) "\
                     "VALUES(?,?,?)", \
                     (str(base64.b64encode(cmd.encode(settings.DEFAULT_CODEC)).decode()), \
                      str(base64.b64encode(shell.encode(settings.DEFAULT_CODEC)).decode()), \
                      str(settings.HTTP_HEADER)))
      conn.commit()
      conn.close()
    except sqlite3.OperationalError as err_msg:
      settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
    except (TypeError, AttributeError) as err_msg:
      pass

"""
Export successful command execution outputs from session file.
"""
def export_stored_cmd(url, cmd, vuln_parameter):
  try:
    if not menu.options.flush_session:
      conn = sqlite3.connect(settings.SESSION_FILE)
      output = None
      conn = sqlite3.connect(settings.SESSION_FILE)
      if settings.TESTABLE_PARAMETER:
        cursor = conn.execute("SELECT output FROM " + table_name(url) + \
                              "_ir WHERE cmd='" + base64.b64encode(cmd.encode(settings.DEFAULT_CODEC)).decode() + "' AND "\
                              "vuln_parameter= '" + vuln_parameter + "';").fetchall()
      else:
        cursor = conn.execute("SELECT output FROM " + table_name(url) + \
                            "_ir WHERE cmd='" + base64.b64encode(cmd.encode(settings.DEFAULT_CODEC)).decode() + "' AND "\
                            "vuln_parameter= '" +  settings.HTTP_HEADER + "';").fetchall()

      conn.commit()
      conn.close()

      for session in cursor:
        output = base64.b64decode(session[0])
      try:
        return output.decode(settings.DEFAULT_CODEC)
      except AttributeError:
        return output
    else:
      no_such_table = True
      pass
  except sqlite3.OperationalError as err_msg:
    pass

"""
Import valid credentials to session file.
"""
def import_valid_credentials(url, authentication_type, admin_panel, username, password):
  try:
    conn = sqlite3.connect(settings.SESSION_FILE)
    conn.execute("CREATE TABLE IF NOT EXISTS " + table_name(url) + "_creds" + \
                 "(id INTEGER PRIMARY KEY, url VARCHAR, authentication_type VARCHAR, admin_panel VARCHAR, "\
                 "username VARCHAR, password VARCHAR);")

    conn.execute("INSERT INTO " + table_name(url) + "_creds(url, authentication_type, "\
                 "admin_panel, username, password) VALUES(?,?,?,?,?)", \
                 (str(url), str(authentication_type), str(admin_panel), \
                 str(username), str(password)))
    conn.commit()
    conn.close()
  except sqlite3.OperationalError as err_msg:
    settings.print_data_to_stdout(settings.print_critical_msg(err_msg))
  except sqlite3.DatabaseError as err_msg:
    checks.error_loading_session_file()

"""
Export valid credentials from session file.
"""
def export_valid_credentials(url, authentication_type):
  try:
    if not menu.options.flush_session:
      conn = sqlite3.connect(settings.SESSION_FILE)
      output = None
      conn = sqlite3.connect(settings.SESSION_FILE)
      cursor = conn.execute("SELECT username, password FROM " + table_name(url) + \
                            "_creds WHERE url='" + url + "' AND "\
                            "authentication_type= '" + authentication_type + "';").fetchall()

      cursor = ":".join(cursor[0])
      return cursor
    else:
      no_such_table = True
      pass
  except sqlite3.OperationalError as err_msg:
    pass

# eof