VoterContactManager
===================

A Grails app to handle multiple state voter data in a single framework and handle linked and unlinked contacts as well.

On a practical matter, the data is broken down into unique elements to reduce duplication and help matching. Common names,
phone numbers, addresses, precincts etc are matched on import. This is intended to keep the "set" compact and enable
better reporting capabilities. Moving forward, I intend to be able to produce walking lists (particularly since addresses
are unique so voters can be tracked by "household". 

Currently, the import feature is nearing completion for Florida. Georgia, for now, is the only other state this app handles
BUT is designed to scale to hold data for all 50 states.

One note, the "initial" import will be time consuming. Once you have your base of addresses and names, updates will happen
WAY quicker (as they will be matching data already in the system aside from any changes found).

Once the Florida import is finally completes the debugging process (almost complete), I'll finish doing the same with the 
search function. Search will allow you to find voters based on criteria and allow you to download that "set" as an Excel
spreadsheet. I'll be working on a mechanism to allow you to "add" results from searches to a walking list you may be building
and use the Google Map API to generate a map of locations.