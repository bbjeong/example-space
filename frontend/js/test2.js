let boy = {
    name: 'mike'
    , showName: function() {
        console.log(this.name);
    }
};

let man = boy;

debugger

boy.name='tom';
debugger
boy = null;
debugger

man.showName();