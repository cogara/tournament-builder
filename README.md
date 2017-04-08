### Java - Tournament Builder
Simple application using Swing UI to create a tournament bracket.

##### Features List
- Done [MVP - Additional functionality will be added on some features]
    - Create Bracket
    - Add new round
    - Remove Round
    - Declare winner to automatically move team to next round - declare champion if final round.
    - Add teams to bracket dynamically for selection
    - Edit existing teams (name, no other team info added yet)
    - Select Team Directly for team slots
- In Progress
    - Build API to store bracket info in database
        - Building methods to convert bracket class into JSON and converting JSON into bracket class
- To Do
    - Edit mode for bracket owner - change teams, edit teams, select winner, add rounds, etc. (started, but currently scrapped to work on more core functionality).
    - View external brackets with link - view only mode.
    - Add players to be assigned to teams
    - View Team info (players, stats)
    - deploy as Java Web Start Application