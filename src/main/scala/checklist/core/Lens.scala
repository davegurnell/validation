package checklist.core

import monocle.PLens

case class PathLens[S, T, A, B](path: Path, lens: PLens[S, T, A, B])