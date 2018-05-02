# batcher
Command-line tool to batch operations, pause, save, and control concurrency

## Features for 1.0.0

* [X] Input support for multiple inputs
* [ ] Output support to define an output directory
    * [X] Specify directory and merge with inputs
    * [ ] Configure Copy, Move, and Synchronize
    * [ ] Previous `type` is retained for future (set synchronize once and it defaults going forward)
* [ ] Start a single background process
    * [ ] Thread-count control
    * [ ] Copy
    * [ ] Move
    * [ ] Synchronize
    * [ ] Stop support
* [ ] Monitoring of active processing
* [ ] Bash completion support
    * See https://debian-administration.org/article/317/An_introduction_to_bash_completion_part_2
    * See https://iridakos.com/tutorials/2018/03/01/bash-programmable-completion-tutorial.html
* [ ] Unarchive support
* [ ] Support remote input and output
* [ ] Bandwidth throttling
* [ ] Arbitrary commands
* [ ] `install` command to configure local or global use