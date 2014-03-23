============
Contributing
============

If you want to contribute you need to follow these guidelines. Otherwise your pull request will
**not be accepted**.

Setup
-----

Fork `adk-toolkit`_ repository on GitHub and follow these steps:

* Clone your repository locally
* Pull upstream changes into your fork regularly

It’s a good practice to pull upstream changes from master into your fork on a regular basis,
infact if you work on outdated code and your changes diverge too far from master, the pull request
has to be rejected.

To pull in upstream changes::

    git remote add upstream https://github.com/palazzem/adk-toolkit
    git fetch upstream

Then merge the changes that you fetched::

    git merge upstream/master

For more info, see http://help.github.com/fork-a-repo/

.. note::
    Please be sure to rebase your commits on the master when possible, so your commits can be
    fast-forwarded: I'm trying to avoid merge commits when they are not necessary.

.. _adk-toolkit: https://github.com/palazzem/adk-toolkit

Issues
------

You can find the list of bugs, enhancements and feature requests on the `issue tracker`_. If you want
to fix an issue, pick up one and add a comment stating you’re working on it. If the resolution
implies a discussion or if you realize the comments on the issue are growing pretty fast, move
the discussion to the `Google Group`_.

.. _issue tracker: https://github.com/palazzem/adk-toolkit/issues
.. _Google Group: https://groups.google.com/forum/#!forum/android-adk-toolkit/

How to get your pull request accepted
-------------------------------------

All Android ADK community want your code, so please follow these simple guidelines to make the
process as smooth as possible.

Run the tests!
~~~~~~~~~~~~~~

The first thing the core committers will do is to run all tests. Any pull request that fails this
test suite will be **immediately rejected**.

Add the tests!
~~~~~~~~~~~~~~

Even if the code coverage is not a good metric for code quality, it's better to add tests when you
add code. If you find an issue that could be reproduced with a test, just add this test and solve
the problem with a bugfix.

Code conventions matter
~~~~~~~~~~~~~~~~~~~~~~~

There are no good nor bad conventions, just follow official `code style guidelines`_ and nobody
will argue. Try reading the code and grasp the overall philosophy regarding method and variable
names, avoid black magics for the sake of readability, keep in mind that simple is better than
complex.

.. _code style guidelines: http://source.android.com/source/code-style.html
