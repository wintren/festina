# Festina Work Sample
> Niklas Wintrén

## Thoughts and Work Process (Diary) 

* Before I got the assignment I started setting up the :studio module as it's just a bunch of setup, but it would make any UI work I wanted to do much easier. But then I got the assignment and decided it's not worth pursuing since UI is more or less irrelevant for this specific task.

This is something I really like as it separates User facing resources and app implementation. It also created a good space for components and compose utility

---

* Identifying the Models sent I am relived that I don't need to implement the actual connection logic etc. and will treat the Devices as a `Data Source`. Therefor they will be placed in the `:data` module and I will have some domain / business logic to connect to them. I will not put a lot of Effort into UI at all - but having a product spec which often comes in the form of UI creates a nice access point and often helps me figure out any pain points or hidden requirements. So I will create the UI first.
* If I would have had any navigation I would have opted for Compose navigation, but since this app is very small this can easily be handled on one screen and one ViewModel etc.
* Even if the main purpose of the assignment is to do the actual connection and handling - I still document my thoughts per model and I am thorough to have a screen architecture I feel comfortable with and that shows how I prefer to work.
* `@PreviewParameter`  doesn't want to work properly so discarding it.

---

* I will need dependency injection - adding Hilt
* I have a particular setup for ViewModels and Screens - importing my invention `StateViewModel` and `EventViewModel` (etc.)
* Would be nice with a logging library like Timber, or something custom - will make do with Log and print for this project.
* Events in Screen/ViewModel are usually navigation based, but also toasts as they rely on context (Platform) so I'll make sure to toast something to show this.
* I have some special comment highlighting (wip, note, whatif, wtf) in addition to todo and fixme - so just imagine those would have some nice color ;)
* Ideas regarding the Repository and logic is emerging: Devices will be acquired in two ways (probably/so far); a listing and a flow of a certain device's information/output.
* LUNCH ^^
* ViewModel and Hilt in Place

---

* Staring with domain and data logic - will need modules. (Don't need modules to do this - but this is a showcase so it should be available)
* Introducing Modules:
  * DI: Used to start Hilt injection graphs for all modules. Moving App DI here to make it hold something; but it can be empty.
  * Core: Utility function that I might want to use in either: `:app`, `:domain`, `:data` and `:studio`
  * Domain: "Business Logic" with `Domain Models`, repositories (definitions), use cases, analytics, logic handling
  * Data: Implement the repositories in `:domain` and handle all Data Sources to provide the app (domain) with all models/data.
* Created a Repo, its implementation, a use case and all connecting tissue to make it all fit together

---

* Work on the Actual Provided models and logic starting. This is done with not much time left of the assignment but I am hopeful that the well structured foundation will make it easy for me to incorporate the functionality.
* I am cleaning up the models I got provided; naming, structure etc.
* Took a break when receiving people that want to buy my Piano.
* Fixed a bug in UseCase where only exception thrown in a flow was being caught, and not surrounding function.

---

* I definitely did not go about this the correct way. Creating a UI with states for two devices like I did just created UI logic that needs solving which took some time even when not wanting to dive too deep into it.
* If I would have started out with a scrappy Repo / Data Source solution from the start I would have been able to catch how the actual data flow would have been and I wouldn't have painted myself into a corner where observability would not be be such a big target.
* Solution is not complete, but there are things in place for discussion and I hope that it will suffice
* Next step would be to start investigating the Disconnect from '456' when it reaches next step of the log, and why it afterwards remain connectable. Some Repo state probably
* Conclusion here is that I invented more requirements for myself than what was asked for and more requirements of course takes more time, and when you also start in the wrong end - well, you end up where I've done. 
