let totalWaitingCount = 0;

class Fork {
    constructor(id) {
        this.state = 0;
        this.id = id
    }

    takeFork(cb, waitingTimeMs = 1) {
        if(this.state === 0) {
            this.state = 1;
            cb();
        }
        else {
            setTimeout(() => this.takeFork(cb, waitingTimeMs), waitingTimeMs * 2)
        }
    }

    acquire() {
        // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
        // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
        // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
        // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
        //    i ponawia probe itd.
        return new Promise((resolve) => { setTimeout(() => { this.takeFork(resolve) }, 1) })
    }

    release() { 
        this.state = 0; 
    }
}

class Waiter {
    constructor(N) {
        this.state = N;
    }

    addWaitingClient(cb, waitingTimeMs = 1) {
        if(this.state > 0) {
            this.state -= 1;
            cb();
        }
        else {
            setTimeout(() => this.addWaitingClient(cb, waitingTimeMs), waitingTimeMs * 2)
        }
    }

    askWaiterAboutForks() {
        return new Promise((resolve) => this.addWaitingClient(resolve))
    }

    infromWaiterAboutTakingBothForks() {
        this.state += 1;
    }
}

class Philosopher {

    constructor(id, forks) {
        this.id = id;
        this.f1 = forks[id % forks.length];
        this.f2 = forks[(id+1) % forks.length];
    }

    eatAndRelease() {
        // console.log('Phillosopher nr', this.id, 'release forks', this.f1.id, this.f2.id);
        this.f1.release();
        this.f2.release();
    }
    
    takeLeft() {
        // console.log('Phillosopher nr', this.id, 'try to take fork nr', this.f1.id);
        return this.f1.acquire()
    }

    takeRight() {
        // console.log('Phillosopher nr', this.id, 'try to take fork nr', this.f2.id);
        return this.f2.acquire()
    }

    async startNaive(count) {
        // zaimplementuj rozwiazanie naiwne
        // kazdy filozof powinien 'count' razy wykonywac cykl
        // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
        for(let i =0; i< count; i++) {
            await this.takeLeft();
            await this.takeRight();
            this.eatAndRelease();
        }
    }

    async startAsym(count) {
        // zaimplementuj rozwiazanie asymetryczne
        // kazdy filozof powinien 'count' razy wykonywac cykl
        // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
        for(let i =0; i< count; i++) {
            let startWaitingTime = new Date().getTime();
            if(this.id % 2 === 0) {
                await this.takeRight();
                await this.takeLeft();
            }
            else {
                await this.takeLeft();
                await this.takeRight();
            }
            totalWaitingCount += new Date().getTime() - startWaitingTime;
            this.eatAndRelease();
        }
    }

    async startConductor(count, waiter) {
        // zaimplementuj rozwiazanie z kelnerem
        // kazdy filozof powinien 'count' razy wykonywac cykl
        // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
        for(let i =0; i< count; i++) {
            let startWaitingTime = new Date().getTime();
            await waiter.askWaiterAboutForks();
            await this.takeLeft();
            await this.takeRight();
            totalWaitingCount += new Date().getTime() - startWaitingTime;
            this.eatAndRelease();
            waiter.infromWaiterAboutTakingBothForks();
        }
    }
}

const runTests = async (isAsym) => {
    for(let j=5; j <= 1000; j+= 5) {
        var N = j;
        totalWaitingCount = 0;
        var forks = [];
        var philosophers = []
        const waitForAll = [];
        const waiter = new Waiter(N-1);
    
        for (var i = 0; i < N; i++) {
            forks.push(new Fork(i));
        }
        
        for (var i = 0; i < N; i++) {
            philosophers.push(new Philosopher(i, forks));
        }
    
        for (var i = 0; i < N; i++) {
            if(isAsym) {
                waitForAll.push(philosophers[i].startAsym(10));
            }
            else {
                waitForAll.push(philosophers[i].startConductor(10, waiter));
            }
        }
        await Promise.all(waitForAll);
        console.log(isAsym ? "JFA" : "JFC", N, totalWaitingCount);
    }
}

const runAllTests = async () => {
    await runTests(true); // asym
    await runTests(false); // with waiter
    console.log('end');
}

runAllTests();

// this code causes deadlock, every philosopher takes the left fork and then everyone wait for the right fork
// for (var i = 0; i < N; i++) {
//     philosophers[i].startNaive(10);
// }

