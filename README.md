# Festina Work Sample
> Niklas Wintrén

## Thoughts and Work Process (Diary) 

* Before I got the assignment I started setting up the :studio module as it's just a bunch of setup, but it would make any UI work I wanted to do much easier. But then I got the assignment and decided it's not worth pursuing since UI is more or less irrelevant for this specific task.

---

* Identifying the Models sent I am relived that I don't need to implement the actual connection logic etc. and will treat the Devices as a `Data Source`. Therefor they will be placed in the `:data` module and I will have some domain / business logic to connect to them. I will not put a lot of Effort into UI at all - but having a product spec which often comes in the form of UI creates a nice access point and often helps me figure out any pain points or hidden requirements. So I will create the UI first.
* If I would have had any navigation I would have opted for Compose navigation, but since this app is very small this can easily be handled on one screen and one ViewModel etc.
* Even if the main purpose of the assignment is to do the actual connection and handling - I still document my thoughts per model and I am thorough to have a screen architecture I feel comfortable with and that shows how I prefer to work.
* `@PreviewParameter`  doesn't want to work properly so discarding it.

