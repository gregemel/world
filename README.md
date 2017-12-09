# World
An immersive world to explore on your Android device.

Built with 3D LibGDX, Ashley, Bullet, Blender.


Currently, the world is limited to fighting creatures in a stone wall arena, first person shooter style.
The libDGX based application targets Android, but the core module can run on a Linux desktop for quick testing.    

### android controls:
 * move with left disk
 * aim and fire with right disk

### desktop controls:
 * move with W, Z, A, S
 * jump with space
 * point with mouse
 * fire with mouse button

## Purpose
* An exploration in building immersive 3D games for Android tablets and phones.
* Learning about extending open source 3D worldAdapter engines for Java on Android.
* Finding ways to amuse my kids.

## Next
* file driven worlds, scenes, etc.
* more interesting scenes and creatures, models, and textures
* goap driven creature ai
* tutorials, maybe...
* more...


##### Inspired by the book:
Building a 3D Game with LibGDX
by Sebastián Di Giuseppe; Elmar van Rijnswou; Andreas Krühlmann

Creature, scene models, textures came from the book's repository and may be copyrighted.
* https://github.com/DeeepGames/SpaceGladiators
*
* more models and textures at: https://opengameart.org/

#### copyright
This source code is made available under the MIT license, which basically means enjoy it, use it, change it, make money off of it, but please don't ever sue me if things go wrong.


# More Code Details
* databags
* systems
* services
* WorldAdapter

#### purpose
Why is the code divided up into these 3 directories and a java file?
* making complex code easier to understand
* grouping code by functional purpose
* a databag holds data, like monster properties
* a system updates some kind of continuous process, like moving a monster
* a service is a tool used by systems or other services, like move monster
* the WorldAdapter connects World to Android

#### behavior and state
* code that separates data from action.  

#### single purpose principle
* A java class should do only 1 thing and do it well.

## databags
What is a databag?
* A databag is a java class that holds data.  
* It can only hold data and not do any action, like math, drawing, or physics.
* data like monster location and scenery model

### World
* the owner of everything
* a collection of scenes

### Scene
* a collection of creatures, scenery, and sounds
* a player start location

### component
* a collection of properties associated with an entity (e.g. position)
* tagging and tracking an entity in your world
* animation state, ai state
* models and skins

### systemstate
* scratch pads for systems

## systems
What is a system?
* A system performs a function, over and over again.
* systems have frame rates (e.g. 60fps)
* handles things that change, like graphics, input, and motion
* uses services

## services
What is a service?
* doing one thing and doing it well
* used by systems to do things like graphics, input, and motion
* "stateless", static, no properties or class variables
* driven only by parameters

### drawers
* tools for drawing world images, like models, particles, and shadows

### factories
* tools for making instances of databags or systems

### loaders
* tools for loading files like worlds, scenes, models, etc.

### ui
* user interface code

### updaters
* services for updating world objects, like animation, player input, ai