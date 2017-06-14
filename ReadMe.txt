1. Original ToDoMvp can be found at here
   https://github.com/googlesamples/android-architecture/tree/todo-mvp/

   All Versions of ToDoMvp :
   https://github.com/googlesamples/android-architecture

2. Concept of Presenter Preservation upon config changes can be found at here
   https://medium.com/@czyrux/presenter-surviving-orientation-changes-with-loaders-6da6d86ffbbf#.iazpmkex9
   Sample Code: 
   https://github.com/czyrux/MvpLoaderSample
   Note: Sample Contains simple fragment and ViewPager inside fragment use cases
   
   Alternative way of using Loader : https://github.com/michal-luszczuk/tomorrow-mvp 

3. Adopted Loader concept to ToDo MvP
   Drive Link:
   
   Changes : Presenter created in Fragment onActivityCreated() insted of Activity() onCreate()
             Provided Callbacks to inform Presenter ready to Fragment.
             View Attached to Presenter in onResume and onActivityResult() detached in onPause() can be changed to onStop() as well.