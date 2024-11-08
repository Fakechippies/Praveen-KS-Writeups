#!/usr/bin/env python

"""Setup script for the 'uncompyle' distribution."""

from distutils.core import setup, Extension

setup (name = "uncompyle2",
       version = "1.1",
       description = "Python byte-code to source-code converter",
       author = "Hartmut Goebel",
       author_email = "h.goebel@crazy-compilers.com",
       url = "http://github.com/wibiti/uncompyle",
       packages=['uncompyle2'],
       scripts=['scripts/uncompyle2'],
      )
