# TBBSystem
A system designed to handle complex turn-based strategy game mechanics. Includes a basic GUI.

# Branch Objective:
Refactor code to improve readability and performance.

# Current Features:
*Basic GUI
*File Loading Menu (functionality missing)
*Basic entity mechanics

# TODO:
*Improve GUI
*Load/Save Files
*More ability mechanics

# Known Bugs:
*If multiple entities are dead at round end, one of the dead entities is not removed properly
*Interface does not scale to handle multiple abilities properly

# Changelog:
1/12/17
*Added action mechanics: an entity can only make one action during its turn
*Moved all ability subclasses to new package, abilityAttributes
*Added new entity to test field to test custom abilities
*Deprecated ABILITY_TIMING enum to use the AbilityTiming class
*Added new ability constructor to allow for custom names

12/22/16
*Rounds now end properly

12/14/16:
*Attempted to add round support

12/13/16: Started changelog
*Added handling for entity death
*Fixed GUI bugs