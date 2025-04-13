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
* 
