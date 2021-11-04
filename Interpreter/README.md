- Toy Language Description
A program (Prg) in this language consists of a statement (Stmt) as follows:
Prg := Stmt where the symbol "::=" means "a Prg is defined as a Stmt".
A statement can be either a compound statement (CompStmt), or an assignment statement 
(AssignStmt), or a print statement (PrintStmt), or a conditional statement (IfStmt) as follows:

Stmt ::= Stmt1 ; Stmt2 /* (CompStmt)*/
 | Type Id /* (VarDecl) */
 | Id = Exp /* (AssignStmt)*/
 | Print(Exp) /* (PrintStmt)*/
 | If Expr Then Stmt1 Else Stmt2 /* (IfStmt)*/
 | nop /* No operation */
where the symbol "|" denotes the possible definition alternatives.

A type Type can be :
Type ::= int
 | bool
A value Value in our language can be either an integer number (ConstNumber), or boolean 
values like (ConstTrue) or (ConstFalse):
Value ::= Number /*(ConstNumber)*/
 | True /* (ConstTrue)*/
 | False /* (ConstFalse)*/
An expression (Exp) can be either a value (Value), or a variable name (Var), or an arithmetic 
expression (ArithExp), or a logical expression (LogicExp), as follows:
Exp ::= Value /*Value*/
 | Id /*(Var)*/
 | Exp1 + Exp2 /*(ArithExp)*/
 | Exp1 - Exp2
 | Exp1 * Exp2
 | Exp1 / Exp2
 | Exp1 and Exp2 /*(LogicExp)*/
 | Exp1 or Exp2
 
- Example1:
int v;
v=2;
Print(v)
- Example2:
int a;
a=2+3*5;
int b;
b=a-4/2 + 7;
Print(b)
- Example3:
bool a;
a=false;
int v;
If a Then v=2 Else v=3;
Print(v)

- Toy Language Evaluation (Execution):
Our mini interpreter uses three main structures:
– Execution Stack (ExeStack): a stack of statements to execute the currrent program
– Table of Symbols (SymTable): a table which keeps the variables values
– Output (Out): that keeps all the mesages printed by the toy program
