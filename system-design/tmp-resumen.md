
Buscar en el vídeo
Who should watch this?
0:00
This video is on the basics of system design.
0:02
If you have never designed a system before, this is probably the place to start.
Software Engineering 101
0:06
So imagine you have a computer with you in which you have written an algorithm.
0:10
So some code is running on this computer and this code is
0:15
like a normal function. It takes some input and it gives out an output.
0:19
Now people look at this code and they decide that this is really useful to them.
0:23
So they're ready to pay you so that they can use that code.
0:27
Now you cannot go around giving your computer to everybody.
0:29
So what you do is you expose your code using some
0:34
protocol, which is going to be running on the internet,
0:37
and by exposing your code using something called an
0:42
a p i application programmable interface, when your code does run,
0:46
it'll give an output and instead of storing that in the file or storing it in
0:49
some database or something like that,
0:51
you return that and that's called a response. Interestingly,
0:56
the thing that is sent to you is called a request
1:01
where people request you. So that's what it is. There's a request sent,
1:05
and for each request,
1:07
there's a corresponding response that your computer will be sending back.
1:10
Imagine setting up this computer.
Problems with self-hosting
1:13
It might require a database to be connected to it.
1:15
It's within the desktop itself.
1:18
You might require to configure these endpoints that people are connecting to.
1:22
And you also need to take into consideration what happens if there's a power
1:26
loss. If someone pulls the plug or something like that,
1:28
you cannot afford to have your service go down because there's lots of people
1:32
paying money for you. You should host your services on the cloud.
Using Cloud Solutions
1:36
So what's the difference between a desktop and a cloud? Nothing really.
1:41
The cloud is a set of computers that somebody provides to you for money,
1:45
of course. So if you pay a cloud solution, for example,
1:50
Amazon Web Services, which is the most popular one, if you pay these guys,
1:55
they're going to give you computation power.
1:57
Computation power is nothing but a desktop that they have somewhere which can
2:01
run your algorithm. How will you actually store your algorithm in that desktop?
2:06
Well, you can do something like a remote login into that
2:11
desktop. That's what the cloud is. It's a set of desktops,
2:15
not necessarily desktops,
2:16
but a set of computers that you can use to run your service.
2:21
The reason we like to do this is because the configuration,
2:23
the settings the reliability can be taken care of to a large extent by the
2:28
solution providers.
2:30
So now that we have our server hosted on a cloud,
2:33
which is basically some computer that we don't know about,
2:37
we can focus on the business requirements.
Scaling your Business
2:41
What business requirements could we possibly have? Well,
2:44
there's lots of people who are using algorithm now,
2:49
and it gets to a point where the code that you have running on the machine is
2:53
not able to handle all of these connections. So what do you do?
2:58
One of the solutions is to buy a bigger machine, Right?
3:03
This is solution number one. The solution number two is to buy more machines.
3:10
The ability to handle more requests by buying more machines or buying bigger
3:14
machines is called scalability.
3:18
And this is a very important term that we need to understand. Well,
3:22
like we said, we can handle more requests by throwing more money at the problem.
What is Vertical Scaling?
3:27
When you're buying bigger machines,
3:29
it means that your computer's going to be larger and therefore it can process
3:32
the requests faster. So that is called vertical scaling.
What is Horizontal Scaling?
3:38
And when you're buying more machines,
3:40
it means that the request can fall on any one of these machines and it'll be
3:44
processed, but because you have more of them,
3:46
the requests can be randomly distributed amongst the machines that you have just
3:50
bought. And that is called horizontal scaling.
3:54
These are two mechanisms by which you can increase the scalability of your
3:58
system. Like we said, scalability is being able to handle more requests.
4:03
Like any two approaches, we can compare them with the pros and cons.
4:07
The first one that we have talked about is we need some sort of load balancing
Horizontal vs. Vertical Scaling
4:11
here. Well, that's not the case here.
4:17
If you have a single machine, there's no load to balance as such.
4:21
The second point is that with lots of machines,
4:24
if one of the machine fails, you can redirect the request to the other ones.
4:29
While over here, there's a single point of failure.
4:32
So this is a single point failure and here
4:37
it is resilient. The third thing to note is that
4:43
all the communication that we have between the servers will be over the network
4:47
and network calls us slow.
4:48
It's io while over here you have interprocess communication.
4:52
So that is quite fast. So here,
4:55
there is interprocess communication.
5:00
While over here we have network calls
5:04
Between two services. So that is remote procedure calls.
5:08
So this is slow and this is fast.
5:11
The fourth point is data consistency. For example,
5:15
let's say you are having a transaction where 3 cents some data to four and then
5:19
4 cents it to five and 5 cents it to one.
5:22
Here you see that the data is complicated to maintain.
5:26
If there is a transaction where the operation has to be atomic,
5:30
what could happen is that we have to lock all the servers, right?
5:33
All the databases that they're using, which is impractical.
5:36
So usually what happens is we have some sort of loose transactional guarantee,
5:40
and that's, that's the reason why here, the data consistency is a real issue.
5:47
While over here, there's just one system on which all the data rec resides,
5:51
and that's why this is consistent.
5:54
The final point deals with some hardware limitations that you're gonna have
5:58
because we cannot just make the computer bigger and bigger and bigger and solve
6:01
the problem. There's going to be some hardware limit that we have here.
6:06
Point number five, and over here,
6:08
this scales well in the sense that the,
6:12
the amount of servers that you throw at the problem is almost linear in terms of
6:16
how many users are added.
6:21
These are the five key differences that vertical scaling and horizontal scaling
6:24
have. So what do you think is used in the real world? Both.
Practical Considerations
6:30
we take some of the good qualities of vertical scaling,
6:32
which is really fast into process communication and the data being consistent.
6:37
So the cache is going to be consistent. There's no dirty reads, dirty rights,
6:41
so to speak.
6:42
We take these two good qualities from here and we take these two good
6:46
qualities from here, which is it scales well because the,
6:50
there's a hardware limit over here and it's also resilient in the sense that if
6:53
one of the server crashes, somebody else can come up. Okay?
6:58
So the hybrid solution is essentially horizontal scaling only,
7:02
where each machine has a big box.
7:07
I mean each machine, you try to take as big a box as possible,
7:10
as feasible money-wise. And then we,
7:14
we pick up a solution this way.
7:16
Initially you can vertical scale as much as you like later on when the users
7:20
start trusting you, you should probably go for horizontal scaling.
7:23
So these are the major considerations we have when designing a system.
7:27
Is it scalable? Is it resilient? And is it consistent
7:32
with these qualities? There's always gonna be some trade-offs that we have,
Conclusion
7:35
and that's what system design is.
7:37
We design a system which is going to meet the requirements,
7:40
and the requirements are such that it's going to be Computer science way
7:45
is possible to actually build a system like this.
7:47
If you have any doubts or suggestions, you can leave them in the comments below.
Thank you!
7:51
If you want notifications for further videos,
7:53
hit the subscribe button and I'll see you next time.